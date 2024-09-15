package com.ChinaMarket.Ecommerce.Service;


import com.ChinaMarket.Ecommerce.Exception.CustomerNotFoundException;
import com.ChinaMarket.Ecommerce.Model.Card;
import com.ChinaMarket.Ecommerce.Model.Customer;
import com.ChinaMarket.Ecommerce.Repository.CardRepository;
import com.ChinaMarket.Ecommerce.Repository.CustomerRepository;
import com.ChinaMarket.Ecommerce.RequestDTO.CardRequestDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.CardDto;
import com.ChinaMarket.Ecommerce.ResponseDTO.CardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CustomerRepository customerRepository;

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException {

        //check customer hai ki nahi
        Customer customer;

        try{
            customer = customerRepository.findById(cardRequestDto.getCustomerId()).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid customer id");
        }

        //agar customer hai to
        // Make a card object
        Card card = Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cvv(cardRequestDto.getCvv())
                .cardType(cardRequestDto.getCardType())
                .customer(customer)
                .build();

        // add the card to current card list of customer
        customer.getCards().add(card);

        customerRepository.save(customer); // save both customer and card

        // prepare Response Dto
        CardResponseDto cardResponseDto = new CardResponseDto();
        cardResponseDto.setName(customer.getName());

        // convert every card to cardDto
        List<CardDto> cardDtoList = new ArrayList<>();
        for(Card card1: customer.getCards()){
            CardDto cardDto = new CardDto();
            cardDto.setCardNo(card1.getCardNo());
            cardDto.setCardType(card1.getCardType());

            cardDtoList.add(cardDto);
        }

        cardResponseDto.setCardDtos(cardDtoList);
        return cardResponseDto;
    }
}
