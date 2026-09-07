package org.example.test.sec06;

import com.example.models.sec06.AccountBalance;
import com.example.models.sec06.DepositRequest;
import com.google.common.util.concurrent.Uninterruptibles;
import org.example.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec04ClientStreamingTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec04ClientStreamingTest.class);

    @Test
    public void depositTest() {
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver = this.stub.deposit(responseObserver);

        // initial message - account number
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        requestObserver.onError(new RuntimeException());
//        IntStream.rangeClosed(1, 10)
//                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
//                .map(m -> DepositRequest.newBuilder().setMoney(m).build())
//                .forEach(requestObserver::onNext);

        //notifying the server that we are done
//        requestObserver.onCompleted();

        // at this point out response observer should receive a response
        responseObserver.await();

        Assertions.assertEquals(1, responseObserver.getItems().size());
        Assertions.assertEquals(200, responseObserver.getItems().getFirst().getBalance());
        Assertions.assertNull(responseObserver.getThrowable());
    }


}
