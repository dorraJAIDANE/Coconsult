package com.test.COCONSULT.ServiceIMP;

import com.test.COCONSULT.DTO.OTP;
import com.test.COCONSULT.Interfaces.OTPInterface;
import com.test.COCONSULT.Reposotories.OTPRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpTimeoutException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
@Service
@AllArgsConstructor

public class OTPServiceIMP implements OTPInterface {
    @Autowired
    OTPRepository otpRepository;

    @Override
    public OTP GenerateOTp() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 5); // Set the expiration time to 5 minutes
        Date expiredDate = calendar.getTime();

        OTP otpObject = new OTP();
        otpObject.setIdentification(String.valueOf(otp));
        otpObject.setExpiredDate(expiredDate);
        otpRepository.save(otpObject);
        return otpObject;
    }

    @Override
    public Boolean VerifOTP(String identification) {
        // Retrieve the OTP object from the repository based on the identification
        OTP otp = otpRepository.findByIdentification(identification);

        // Check if the OTP object is null (i.e., not found)
        if (otp == null) {
            return false; // OTP does not exist, so it's invalid
        }

        // Get the expiration date of the OTP
        Date expiredDate = otp.getExpiredDate();

        // Get the current date and time
        Date now = new Date();

        // Check if the current date and time is before the expiration date
        return now.before(expiredDate);
    }


    @Override
    public OTP ResendOTP(OTP existingOTP) {
        // Check if the existing OTP has expired
        Date now = new Date();
        if (existingOTP.getExpiredDate().before(now)) {
            // Generate and save a new OTP with updated expiration date
            return GenerateOTp();
        } else {
            // OTP is still valid, return the existing OTP without generating a new one
            return existingOTP;
        }
    }

    @Override
    public void DeleteALLOTP() {
        otpRepository.deleteAll();
    }
}
