package com.skilldistillery.eventtracker.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldistillery.eventtracker.entities.FuelTracker;
import com.skilldistillery.eventtracker.repositories.FuelTrackerRepository;

@Service
public class FuelTrackerServiceImpl implements FuelTrackerService {
	@Autowired
	private FuelTrackerRepository ftrepo;

	@Override
	public List<FuelTracker> getAllEntries() {
		return ftrepo.findAll();
	}

	@Override
	public FuelTracker getById(Integer id) {
		Optional<FuelTracker> opTracker = ftrepo.findById(id);
		if (opTracker.isPresent()) {
			return opTracker.get();
		}
		return null;
	}

	@Override
	public FuelTracker newEntry(FuelTracker entry) {
		return ftrepo.saveAndFlush(entry);
	}

	@Override
	public FuelTracker replaceEntry(FuelTracker entry, Integer id) {
		Optional<FuelTracker> opTracker = ftrepo.findById(id);
		if (opTracker.isPresent()) {
			FuelTracker managedEntry = opTracker.get();
			if (entry.getFuelCost() != null) {
				managedEntry.setFuelCost(entry.getFuelCost());
			}
			if (entry.getGallonsPerFill() != null) {
				managedEntry.setGallonsPerFill(entry.getGallonsPerFill());
			}
			if (entry.getMiles() != null) {
				managedEntry.setMiles(entry.getMiles());
			}
			if (entry.getRefuelDate() != null) {
				managedEntry.setRefuelDate(entry.getRefuelDate());
			}
			return ftrepo.saveAndFlush(managedEntry);
		}
		return null;
	}

	@Override
	public Boolean deleteEntry(Integer id) {
		ftrepo.deleteById(id);
		if (!ftrepo.findById(id).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public FuelTracker patchEntry(String json, Integer id) {
		Optional<FuelTracker> opTracker = ftrepo.findById(id);
		if (opTracker.isPresent()) {
			FuelTracker patchedPost = opTracker.get();
			ObjectMapper mapper = new ObjectMapper();
			try {
				patchedPost = mapper.readValue(json, FuelTracker.class);
				return patchedPost;
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
