package com.tophe.ddd.pad.infrastructure;

import com.tophe.ddd.infrastructure.InMemoryRepository;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.persistence.PadRepository;

public class PadInMemoryRepository extends InMemoryRepository<Pad, String> implements PadRepository {

}
