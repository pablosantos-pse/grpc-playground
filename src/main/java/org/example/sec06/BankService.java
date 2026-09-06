package org.example.sec06;

import com.example.models.sec06.AccountBalance;
import com.example.models.sec06.BalanceCheckRequest;
import com.example.models.sec06.BankServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.example.sec06.repository.AccountRepository;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }
}
