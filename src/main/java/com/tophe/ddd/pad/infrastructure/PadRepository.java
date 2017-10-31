package com.tophe.ddd.pad.infrastructure;

import com.tophe.ddd.pad.domain.Pad;
import org.springframework.data.repository.CrudRepository;

public interface PadRepository extends CrudRepository<Pad, String> {

}
