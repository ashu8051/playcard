package com.card.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.card.app.model.Tutorial;
import com.card.app.service.TutorialService;

@Service
@Transactional
public class TutorialServiceImpl extends AbstractTutorialService<Tutorial> implements TutorialService{

}
