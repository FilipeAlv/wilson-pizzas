package br.com.wilsonpizzas.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.adapter.PageAdapterSecundaria;


@SuppressLint("ValidFragment")
public class FragmentPrincipal extends Fragment {
    private HorizontalScrollView horizontalScrollView;
    private  TabLayout tabLayout;
    private  ViewPager viewPager;
    private String[] titles;
    private String tipo;

    @SuppressLint("ValidFragment")
    public FragmentPrincipal(String[] titles, String tipo){
        this.titles=titles;
        this.tipo = tipo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutSecundaria);
        viewPager = view.findViewById(R.id.viewPagerSecundario);

       // horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horiozntalScroll);

        viewPager.setAdapter(new PageAdapterSecundaria(getChildFragmentManager(),titles, tipo));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    public HorizontalScrollView getHorizontalScrollView() {
        return horizontalScrollView;
    }
}
