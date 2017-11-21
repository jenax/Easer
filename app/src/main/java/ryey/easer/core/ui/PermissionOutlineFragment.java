package ryey.easer.core.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import java.util.List;

import ryey.easer.R;
import ryey.easer.commons.plugindef.PluginDef;
import ryey.easer.plugins.PluginRegistry;

public class PermissionOutlineFragment extends Fragment {

    Button mButton;

    public PermissionOutlineFragment() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission_outline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mButton = (Button) view.findViewById(R.id.button_more);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAllPermissions();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasAllRequiredPermissions()) {
            getView().setVisibility(View.GONE);
        } else {
            getView().setVisibility(View.VISIBLE);
        }
    }

    boolean hasAllRequiredPermissions() {
        for (Object obj_plugin : PluginRegistry.getInstance().all().getPlugins()) {
            PluginDef plugin = (PluginDef) obj_plugin;
            if (!plugin.checkPermissions(getContext())) {
                Logger.d("Permission for plugin <%s> not satisfied", plugin.name());
                return false;
            }
        }
        return true;
    }

    void requestAllPermissions() {
        List plugins = PluginRegistry.getInstance().all().getPlugins();
        for (int i = 0; i < plugins.size(); i++) {
            PluginDef plugin = (PluginDef) plugins.get(i);
            plugin.requestPermissions(getActivity(), i);
        }
    }
}
