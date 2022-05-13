package com.dariomorgrane.compledocx.persistence;

import com.dariomorgrane.compledocx.dto.SupplementedDocumentDtoImpl;
import com.dariomorgrane.compledocx.exception.DocumentNotFoundException;
import com.dariomorgrane.compledocx.model.document.SupplementedDocumentImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementedDocumentRepository extends JpaRepository<SupplementedDocumentImpl, Long> {

    Page<SupplementedDocumentDtoImpl> findAllBy(Pageable pageable);

    @Override
    default void deleteById(Long id) {
        delete(findById(id).orElseThrow(
                () -> new DocumentNotFoundException(id)
        ));
    }

}
