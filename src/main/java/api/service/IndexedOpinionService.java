package api.service;

import api.model.Opinion;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xenosky
 * Date: 6/23/13
 * Time: 1:03 AM
  */
public interface IndexedOpinionService {
    List<Opinion> findAll(int i, int i1);

    List<Opinion> findByHolderLike(String holderName);

    List<Opinion> findByEntityLike(String target);

    List<Opinion> findByHolderAndEntityLike(String holderName, String target);

    List<Opinion> addOrUpdateOpinions(List<Opinion> opinions);
}
