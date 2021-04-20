package TravelPackage.utils;

import TravelPackage.dtos.PaymentMethodDTO;

public class Price {

    public static Double getInterest(PaymentMethodDTO paymentMethodDTO){
        if(paymentMethodDTO.getType().equalsIgnoreCase("CREDIT")){
            if(paymentMethodDTO.getDues() <= 3) return 0.05;
            else if(paymentMethodDTO.getDues() <= 6) return 0.10;
            else if(paymentMethodDTO.getDues() <= 9) return 0.15;
            else if(paymentMethodDTO.getDues() <= 12) return 0.20;
        }
        return 0d;
    }
}
