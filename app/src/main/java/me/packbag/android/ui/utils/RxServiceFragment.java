package me.packbag.android.ui.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import rx.subjects.PublishSubject;

public class RxServiceFragment extends Fragment {

    @SuppressWarnings("unchecked")
    public static <T extends RxServiceFragment> T get(AppCompatActivity context, T instance, String tag) {
        T fragment = (T) context.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = instance;
            context.getSupportFragmentManager().beginTransaction().add(fragment, tag).commit();
        }
        return fragment;
    }

    protected PublishSubject<Integer> onDestroy = PublishSubject.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroy.onNext(1);
    }
}
