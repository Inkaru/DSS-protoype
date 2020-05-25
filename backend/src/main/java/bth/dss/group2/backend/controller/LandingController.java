package bth.dss.group2.backend.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LandingController {
	private static final Logger logger = LoggerFactory.getLogger(LandingController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", "/index", "/home" }, method = RequestMethod.GET)
	public String land(Locale locale, Model model) {
		return "index.html";
	}
}
