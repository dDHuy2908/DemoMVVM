package com.ddhuy4298.demomvvm.activities.main;

import androidx.lifecycle.Observer;

import com.ddhuy4298.demomvvm.R;
import com.ddhuy4298.demomvvm.databinding.ActivityMainBinding;
import com.t3h.basemodule.base.ActivityBase;

public class MainActivity extends ActivityBase<ActivityMainBinding, MainViewModel> implements MainListener {

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        binding.setListener(this);
        viewModel.getValue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.setValue(integer);
            }
        });
        viewModel.getIsStarted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.setIsStarted(aBoolean);
            }
        });

    }

    @Override
    public void onStartClicked() {
        viewModel.startCounter();
    }
}
