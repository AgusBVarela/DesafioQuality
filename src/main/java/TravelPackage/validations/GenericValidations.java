package TravelPackage.validations;

import TravelPackage.dtos.PaymentMethodDTO;

public class GenericValidations {

    public static String validatePaymentMethod(PaymentMethodDTO paymentMethodDTO){
        switch (paymentMethodDTO.getType().toUpperCase()){
            case "CREDIT":
                if(paymentMethodDTO.getDues() < 1 || paymentMethodDTO.getDues() > 12) return "El número de cuotas debe ser entre 1 y 12.";
                break;
            case "DEBIT":
                if(paymentMethodDTO.getDues() != 1 ) return "El número de cuotas por pagar con débito debe ser 1.";
                break;
            default:
                return "EL tipo de pago debe ser 'CREDIT' o 'DEBIT'.";
        }
        return "";
    }
}
