package bth.dss.group2.backend.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.StringJoiner;

import bth.dss.group2.backend.domain.Location;
import bth.dss.group2.backend.domain.dto.LocationDTO;
import bth.dss.group2.backend.repository.LocationRepository;
import bth.dss.group2.backend.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
	private final LocationRepository locationRepository;
	private final Queue<String> coordinateRequestQueue;

	@Autowired
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
		this.coordinateRequestQueue = new LinkedList<>();
	}

	public Location getOrCreate(LocationDTO loc) {
		Location newLocation = Util.getMapper().map(loc, Location.class);
		Optional<Location> existingInDB = locationRepository.findAll()
				.stream()
				.filter(l -> l.equals(newLocation))
				.findFirst();
		Location finalLocation;
		finalLocation = existingInDB.orElseGet(() -> locationRepository.save(newLocation));
		coordinateRequestQueue.offer(finalLocation.getId());
		return finalLocation;
	}

	private String getRequest(String url) throws Exception {
		final URL obj = new URL(url);
		final HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		if (con.getResponseCode() != 200) return null;
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	public Map<String, Double> getCoordinates(String address) {
		Map<String, Double> res = new HashMap<>();
		StringBuilder query = new StringBuilder();
		String[] split = address.split(" ");
		String queryResult = null;
		query.append("https://nominatim.openstreetmap.org/search?q=");
		if (split.length == 0) return res;
		for (int i = 0; i < split.length; i++) {
			query.append(split[i]);
			if (i < (split.length - 1)) {
				query.append("+");
			}
		}
		query.append("&format=json&addressdetails=1");
		logger.debug("Query:" + query);
		try {
			queryResult = getRequest(query.toString());
		}
		catch (Exception e) {
			logger.error("Error when trying to get data with the following query " + query);
		}
		if (queryResult == null) return res;
		Object obj = JSONValue.parse(queryResult);
		logger.debug("obj=" + obj);
		if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			if (!array.isEmpty()) {
				JSONObject jsonObject = (JSONObject) array.get(0);
				String lon = (String) jsonObject.get("lon");
				String lat = (String) jsonObject.get("lat");
				res.put("lon", Double.parseDouble(lon));
				res.put("lat", Double.parseDouble(lat));
			}
		}
		return res;
	}

	@Component
	@ConditionalOnProperty("locations.coordinatefetching.enable")
	private class FetchCoordinatesTask {
		@Scheduled(fixedDelay = 5000)
		public void fetchCoordinates() {
			String locationId = coordinateRequestQueue.poll();
			if (locationId == null) return;
			logger.info("Fetching coordinates for location with id " + locationId);
			Optional<Location> maybeLocation = locationRepository.findById(locationId);
			if (maybeLocation.isEmpty()) return;
			Location loc = maybeLocation.get();
			if (loc.getLatitude() > 0 && loc.getLongitude() > 0) return;
			StringJoiner joiner = new StringJoiner(",");
			if (loc.getStreetAddress() != null) joiner.add(loc.getStreetAddress());
			if (loc.getZipCode() != null) joiner.add(loc.getZipCode());
			if (loc.getCity() != null) joiner.add(loc.getCity());
			if (loc.getRegion() != null) joiner.add(loc.getRegion());
			if (loc.getCountry() != null) joiner.add(loc.getCountry());
			if (joiner.length() == 0) return;
			Map<String, Double> coords = getCoordinates(joiner.toString());
			if (coords == null || coords.isEmpty()) return;
			loc.setLatitude(coords.get("lat"));
			loc.setLongitude(coords.get("lon"));
			locationRepository.save(loc);
			logger.info("Retrieved coordinates: " + loc);
		}
	}
}


