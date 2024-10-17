package com.einarr07.curso_spring_boot.repository;

import com.einarr07.curso_spring_boot.model.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InmuebleRepository extends JpaRepository <Inmueble, Long>{

}
