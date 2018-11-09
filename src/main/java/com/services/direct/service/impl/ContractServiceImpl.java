package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Contract;
import com.services.direct.bean.Product;
import com.services.direct.bean.Reduction;
import com.services.direct.data.ContractInputDto;
import com.services.direct.data.ReductionDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.ContractRepository;
import com.services.direct.repo.ProductRepository;
import com.services.direct.repo.ReductionRepository;
import com.services.direct.service.ContractService;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

	private static Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	@Autowired
	private EntityDTOMapper entityDTOMapper;

	private ContractRepository contractRepository;
	
	private ReductionRepository reductionRepository;
	
	private ProductRepository productRepository;

	@Autowired
	public ContractServiceImpl(final ContractRepository contractRepository,
			ReductionRepository reductionRepository,
			ProductRepository productRepository,
			EntityDTOMapper entityDTOMapper) {
		this.contractRepository = contractRepository;
		this.entityDTOMapper     = entityDTOMapper;
		this.reductionRepository = reductionRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Contract getContractByUID(String contractUid) {
		return contractRepository.getContractByUID(contractUid);
	}

	
	@Override
	@Transactional
	public Contract createContract(ContractInputDto contractDto) throws BusinessException {

		Contract contract = entityDTOMapper.contractDtotoContract(contractDto);
		
		if (contract !=null )
		{
			log.info(" ****** contract -> reductions is null ***** ");
			if (contractDto.getReductions() != null && contractDto.getReductions().size() != 0) {
				
				log.info(" ****** contract -> reductions is not null ***** contractDto product : " + contractDto.getReductions().toString());
				contractDto.getReductions().forEach(reduction -> {
					log.info(" ****** ADD product : " + reduction.getProductUid());
				});
				
				List<Reduction> reductions = entityDTOMapper.reductionDtotoReductions(contractDto.getReductions());
				contract.setReductions(reductions);
				
				// add UID
				UUID uuid = UUID.randomUUID();
				contract.setUid(uuid.toString());
				
				// Save Contract 
				Contract contractEntity = contractRepository.save(contract);

				// save reduction
				if (reductions != null && reductions.size() != 0) {

					reductions.forEach(reduction -> {
						log.info(" ****** ADD reductions : " + reduction.getDescription());
						
						// add UID reduction
						UUID reductionUuid = UUID.randomUUID();
						reduction.setUid(reductionUuid.toString());
						
						Product product = this.productRepository.getProductByUID(reduction.getProductUid());
				    	reduction.setProduct(product);
						reduction.setContract(contractEntity);
						// contract.addReduction(reduction);
						reductionRepository.save(reduction);
					});
				} else {
			        throw new FileNotFoundException("Error persist resource reductions", "INTERNAL_ERROR");
				}
			}  else {
				
				log.info(" =================== Pas reductions associés =================== ");
				
				// Save Contract 
				contractRepository.save(contract);
			}
						
			// contractRepository.save(contract);
			log.info(" >>> Contract -> reductions " + contract.getReductions().get(0).getDescription());
			
			return contract;
			
		} else {
			 throw new BusinessException("The resource contract was not found", "FILE_NOT_FOUND");
		}
		
	}

	@Override
	public Contract updateContract(Integer contractId, ContractInputDto contractDto) {
		
		Contract contract = this.contractRepository.getOne(contractId);
		
		if (!contractDto.getName().equals(contract.getName())) contract.setName(contractDto.getName());
		if (!contractDto.getType().equals(contract.getType())) contract.setType(contractDto.getType());

		// @TODO liste de reduction
		return this.contractRepository.save(contract);
	}

	@Override
	public List<Contract> getAllContracts() {
		List<Contract> contracts = (List<Contract>) contractRepository.findAll();
		return contracts;
	}

	@Override
	public void deleteContract(Integer contractId) {
		this.contractRepository.deleteById(contractId);

	}
	
	@Override
	@Transactional
	public void deleteContractByUID(String contractUid) {
		this.contractRepository.deleteContractByUID(contractUid);
		
	}

	@Override
	@Transactional
	public Reduction addReductionByProduct(String contractUid, ReductionDto reductionDto) {
			Reduction reduction = entityDTOMapper.reductionDtotoReduction(reductionDto);
			if (reduction != null) {
				Contract contract = this.contractRepository.getContractByUID(contractUid);
				if (contract != null) {
					
					// add UID reduction
					UUID reductionUuid = UUID.randomUUID();
					reduction.setUid(reductionUuid.toString());
					
					Product product = this.productRepository.getProductByUID(reduction.getProductUid());
			    	reduction.setProduct(product);
	
					reduction.setContract(contract);
					log.info(" ****** Entity reduction ID {} : contractId {}" , reduction.getId() ,  reduction.getContract().getId());
					reductionRepository.save(reduction);
					return reduction;
					
				} else {
					 throw new RuntimeException("The resource contract was not found");
				}
			} else {
				throw new RuntimeException("Erreur de parsing ou erreur JSON");
			}
	}

	@Override
	public Contract unlinkReductionOfContract(String contractUid, String reductionUid) {
		Contract contract = this.contractRepository.getContractByUID(contractUid);
		if (contract != null) {
			contract.getReductions().forEach(reduction -> {
				if (reduction.getUid().equals(reductionUid)) {
					contract.getReductions().remove(reduction);
				}
			});
			
			this.contractRepository.save(contract);
			return contract;
		} else {
			 throw new RuntimeException("The resource File was not found");
		}
	}
	
	
}
