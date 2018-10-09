//package com.services.direct.mapping;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//
//import com.services.direct.bean.Contact;
//import com.services.direct.data.ContactInputDto;
//
//public abstract class ContactMapperImpl implements ContactMapper {
//
//	@Autowired
//    @Qualifier("delegate")
//	private ContactMapper delegate;
//
//	@Override
//	public Contact contactDtoToContact(ContactInputDto contactDto) {
//		Contact contact = delegate.contactDtoToContact(contactDto);
//		
//		return contact;
//	}
//	
//}
