package com.pichincha.practice.util;

import com.pichincha.practice.domain.jpa.Client;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestDataInitializationHelper {

  private boolean isInit = false;
  @Autowired
  private EntityManager em;
  private TestEntityManager testEntityManager;

  public void initOffersTestDataSet() {
    if (isInit) {
      return;
    }

    persist(getValidClient());

    clear();
    isInit = true;
  }

  private void clear() {
    if (testEntityManager != null) {
      testEntityManager.clear();
    } else if (em != null) {
      em.clear();
    }
  }

  private void persist(Object target) {
    if (testEntityManager != null) {
      testEntityManager.persistAndFlush(target);
    } else if (em != null) {
      em.persist(target);
      em.flush();
    }
  }

  public Client getValidClient() {
    return Client.builder()
        .nameType("Chris")
        .identification("1803750312")
        .build();
  }

}
