package com.github.segoh.viewpagerindicator.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {

    private static final String ARG_POSITION = PageFragment.class.getName() + ".position";

    public static PageFragment newInstance(final int position) {
        final PageFragment fragment = new PageFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(getTitle());
        final TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(getText());
    }

    private String getTitle() {
        return getStringFromArray(R.array.titles, getPosition());
    }

    private String getText() {
        return getStringFromArray(R.array.texts, getPosition());
    }

    private String getStringFromArray(final int arrayId, final int index) {
        return getResources().getStringArray(arrayId)[index];
    }

    private int getPosition() {
        return getArguments().getInt(ARG_POSITION, 0);
    }
}
