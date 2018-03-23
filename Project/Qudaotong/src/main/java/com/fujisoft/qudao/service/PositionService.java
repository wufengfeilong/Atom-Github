package com.fujisoft.qudao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fujisoft.qudao.domain.Position;
import com.fujisoft.qudao.repository.PositionRepository;
@Service
public class PositionService {
	 @Autowired
	private PositionRepository positionRepository;
	 
	public List<Position> getPositionList() {
		return  positionRepository.findAll();
	}

}
