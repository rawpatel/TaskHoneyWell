package com.surendra.honeywelltask.presentation.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.surendra.honeywelltask.data.model.SNMPRequest;
import com.surendra.honeywelltask.data.resporsitory.SNMPRepository;


public class SNMPViewModel extends ViewModel {

    private final MutableLiveData<String> snmpData = new MutableLiveData<>();
    private final SNMPRepository repository = new SNMPRepository();

    public LiveData<String> getResult() {
        return snmpData;
    }

    public void getSNMPValue(SNMPRequest request) {
        repository.fetch(request, snmpData::postValue);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.clear();
    }
}
