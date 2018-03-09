package com.qien.gajemee.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.gajemee.domain.Evenement;

@Component
public interface IBerichtRepository extends CrudRepository <Evenement, Long>{

}
