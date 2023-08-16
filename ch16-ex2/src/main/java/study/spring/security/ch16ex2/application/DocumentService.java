package study.spring.security.ch16ex2.application;

import org.springframework.stereotype.Service;
import study.spring.security.ch16ex2.domain.Document;
import study.spring.security.ch16ex2.domain.DocumentRepository;

@Service
public class DocumentService {

  private final DocumentRepository documentRepository;

  public DocumentService(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  public Document getDocument(String code) {
    return documentRepository.findDocument(code);
  }
}
