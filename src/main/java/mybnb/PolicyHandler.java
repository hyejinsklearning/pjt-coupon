package mybnb;

import mybnb.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    CouponRepository couponRepository;

    private int issuePricePoint = 100000;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayApproved_Issue(@Payload PayApproved payApproved){

        if(payApproved.isMe()){
//            System.out.println("##### listener Issue : " + payApproved.toJson());
//            pay price 가 일정 금액 이상일 때 종류 다르게 발행한다.
            Coupon coupon = new Coupon();
            coupon.setGuestId(payApproved.getGuestId());
//            //coupon code 생성 변경하기
            if(issuePricePoint < payApproved.getPrice()) {
                coupon.setCouponCode("20PERCENTDISCOUNT");
            } else {
                coupon.setCouponCode("2000WONDISCOUNT");
            }
//            coupon.setCounponCode("DISCOUNT");
            coupon.setStatus("CouponIssued");
            coupon.setBookId(payApproved.getBookId());

            couponRepository.save(coupon);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_Cancel(@Payload PayCanceled payCanceled){

        if(payCanceled.isMe()){
//            System.out.println("##### listener Cancel : " + payCanceled.toJson());
            Coupon coupon = new Coupon();
            coupon.setGuestId(payCanceled.getGuestId());
            coupon.setStatus("CouponCanceled");
            coupon.setBookId(payCanceled.getBookId());

            couponRepository.save(coupon);
        }
    }

}
