package com.services.direct.utility;

import java.util.ArrayList;
import java.util.List;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;

public class Util {
	
	public static <E> List<E> toList(Iterable<E> iterable) {
	    if(iterable instanceof List) {
	      return (List<E>) iterable;
	    }
	    ArrayList<E> list = new ArrayList<E>();
	    if(iterable != null) {
	      for(E e: iterable) {
	        list.add(e);
	      }
	    }
	    return list;
	  }
	
	/**
	 * Calcule total Commande
	 * @param bordereauDetail
	 * @return
	 * @throws BusinessException
	 */
	public static Double totalCommandCalulate(BordereauDetail bordereauDetail) throws BusinessException {
		
		Double totalLocal = 0.0 ;
		// verifier bordereauDetail isExist
		if (bordereauDetail !=null) {
			
			// verifier bordereauDetail.getProduct isExist
			if (!(bordereauDetail.getProduct() !=null && bordereauDetail.getProduct().getPrice() != null )) {
				throw new BusinessException("Pas de produit attaché au bordereaudetail", "INTERNAL_ERROR");
			}
			// verifier percentage isNotNull
			// getQte
			
			totalLocal = (bordereauDetail.getProduct().getPrice().doubleValue() * bordereauDetail.getQte()) ;
			double reduction = (totalLocal * bordereauDetail.getPercentage()) / 100 ;
			totalLocal = totalLocal - reduction;
			
		} else {
			throw new FileNotFoundException("The resource bordereauDetail was not found", "FILE_NOT_FOUND");
		}
		
		return totalLocal;
		
	}

}
