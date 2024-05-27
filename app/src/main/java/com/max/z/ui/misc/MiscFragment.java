package com.max.z.ui.misc;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.max.z.R;
import com.max.z.databinding.FragmentMiscBinding;

public class MiscFragment extends Fragment {
    private Button button;

    private FragmentMiscBinding binding;
    private MiscViewModel mViewModel;

    public static MiscFragment newInstance() {
        return new MiscFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMiscBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button button = root.findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddNew.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiscViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public MiscViewModel getmViewModel() {
        return mViewModel;
    }

}
