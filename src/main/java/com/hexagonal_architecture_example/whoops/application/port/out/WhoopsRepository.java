package com.hexagonal_architecture_example.whoops.application.port.out;

import com.hexagonal_architecture_example.whoops.domain.Whoops;

public interface WhoopsRepository {
Whoops save(Whoops whops);
}
