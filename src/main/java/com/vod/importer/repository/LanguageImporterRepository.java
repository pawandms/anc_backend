package com.vod.importer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vod.importer.model.LanguageImport;

@Repository
public interface LanguageImporterRepository extends MongoRepository<LanguageImport, String> {

}
