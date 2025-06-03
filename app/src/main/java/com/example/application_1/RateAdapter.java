package com.example.application_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class RateAdapter extends ArrayAdapter<Rate> {
    private final LayoutInflater inflater;

    public RateAdapter(@NonNull Context context, @NonNull List<Rate> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        if (convertView == null) {
            // item_rate.xml 就是你前面创建的行布局文件
            convertView = inflater.inflate(R.layout.item_rate, parent, false);
        }

        Rate rate = getItem(position);
        if (rate != null) {
            TextView tvName = convertView.findViewById(R.id.text_currency_name);
            TextView tvRate = convertView.findViewById(R.id.text_currency_rate);

            tvName.setText(rate.getName());
            tvRate.setText(String.format(Locale.getDefault(), "%.4f", rate.getRate()));
        }

        return convertView;
    }
}
