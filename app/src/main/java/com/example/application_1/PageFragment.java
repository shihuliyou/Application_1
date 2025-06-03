package com.example.application_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {
    private static final String ARG_PAGE = "arg_page";

    public static PageFragment newInstance(int pageIndex) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageIndex);
        f.setArguments(args);
        return f;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inf.inflate(R.layout.fragment_page, container, false);
        int page = requireArguments().getInt(ARG_PAGE, 1);
        TextView tv = root.findViewById(R.id.tv_page);
        FrameLayout bg = root.findViewById(R.id.page_root);

        switch (page) {
            case 1: tv.setText("第一页"); break;
            case 2: tv.setText("第二页"); break;
            case 3: tv.setText("第三页"); break;
        }
        return root;
    }
}
