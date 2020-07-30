package mybnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Coupon_table")
public class Coupon {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long guestId;
    private String couponCode;
    private String status;
    private Long bookId;

    @PostPersist
    public void onPostPersist(){

        if("CouponIssued".equals(getStatus())) {
            CouponIssued couponIssued = new CouponIssued();
            BeanUtils.copyProperties(this, couponIssued);
            couponIssued.setStatus(getStatus());
            couponIssued.publishAfterCommit();
        }
        else if("CouponCanceled".equals(getStatus())) {
            CouponCanceled couponCanceled = new CouponCanceled();
            BeanUtils.copyProperties(this, couponCanceled);
            couponCanceled.setStatus(getStatus());
            couponCanceled.publishAfterCommit();
        }

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
