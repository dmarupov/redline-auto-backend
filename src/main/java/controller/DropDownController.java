package controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import repository.DropDownRepository;

@RestController
@EnableAutoConfiguration
public class DropDownController {

	@Autowired
	DropDownRepository repository;

	@RequestMapping("/makedropdownlst")
	public List<Map<String, Object>> makeDropDownList() {
		return repository.getMakeDropDownList();
	}

	@RequestMapping("/modeldropdownlst")
	public List<Map<String, Object>> modelDropDownList(@RequestParam("vehicleMake") int makeID) {
		return repository.getModelDropDownList(makeID);
	}

	@RequestMapping("/yeardropdownlst")
	public List<Map<String, Object>> yearDropDownList() {
		return repository.getYearDropDownList();
	}
}
