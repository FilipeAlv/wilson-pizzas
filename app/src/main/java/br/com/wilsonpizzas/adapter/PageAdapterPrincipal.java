package br.com.wilsonpizzas.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.wilsonpizzas.fragments.FragmentPrincipal;

public class PageAdapterPrincipal extends FragmentStatePagerAdapter {
    String[] titles, titlesTab1, titlesTab2;
    static FragmentPrincipal fragmentPrincipal;
    public PageAdapterPrincipal(FragmentManager fm, String[] titles, String[] titlesTab1, String[] titlesTab2) {
        super(fm);
        this.titles=titles;
        this.titlesTab1=titlesTab1;
        this.titlesTab2=titlesTab2;

    }

    @Override
    public Fragment getItem(final int position) {
        switch (position){
            case 0:
                fragmentPrincipal = new FragmentPrincipal(titlesTab1,"Salgados");
                return fragmentPrincipal;
            case 1:
                fragmentPrincipal = new FragmentPrincipal(titlesTab2, "Bebidas");
                return fragmentPrincipal;
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public static FragmentPrincipal getFragmentPrincipal() {
        return fragmentPrincipal;
    }
}
