package com.github.partition.nonconfscope;

import com.github.partition.nonconfscope.dagger.NonConfigurationComponent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String KEY_COMPONENT_ID = "component_id";

    private NonConfigurationComponent injector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector = retrieveInjectorOrCreateNew(savedInstanceState);
    }

    private NonConfigurationComponent retrieveInjectorOrCreateNew(Bundle savedInstanceState) {
        NonConfigurationComponent component = null;
        Object lastNonConfInstance = getLastCustomNonConfigurationInstance();
        if (lastNonConfInstance == null) {
            //try to extract from singleton
            if (savedInstanceState != null) {
                String componentId = savedInstanceState.getString(KEY_COMPONENT_ID);
                if (componentId != null) {
                    component = NonConfigurationComponentSaver.getInstance().pull(componentId);
                }
            }
        } else {
            component = (NonConfigurationComponent) lastNonConfInstance;
        }

        //create new if was not restored
        if (component == null) {
            component = NonConfApplication.applicationComponent().nonConfiguration();
        }

        return component;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //store to the singleton only in case the activity is not changing config
        if (!isChangingConfigurations()) {
            String componentId = NonConfigurationComponentSaver.getInstance().push(injector);
            outState.putString(KEY_COMPONENT_ID, componentId);
        }
    }

    protected NonConfigurationComponent getInjector() {
        return injector;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return injector;
    }
}
