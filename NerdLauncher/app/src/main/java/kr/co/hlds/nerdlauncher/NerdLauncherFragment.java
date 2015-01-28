package kr.co.hlds.nerdlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jhpark on 2015-01-28.
 */
public class NerdLauncherFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Toast.makeText(getActivity(), "I've found "+activities.size()+" activities.",
                Toast.LENGTH_LONG).show();

        Collections.sort(activities, new Comparator<ResolveInfo>(){
           public int compare(ResolveInfo a, ResolveInfo b){
               return String.CASE_INSENSITIVE_ORDER.compare(
               a.loadLabel(pm).toString(),
               b.loadLabel(pm).toString());
           }
        });

        setListAdapter(
                new ArrayAdapter<ResolveInfo>(getActivity(),
                        android.R.layout.simple_list_item_1, activities){
                    public View getView(int position, View convertView, ViewGroup container){
                        View v = super.getView(position, convertView, container);
                        TextView tv = (TextView)v;
                        ResolveInfo info = getItem(position);
                        tv.setText(info.loadLabel(pm));
                        return v;
                    }
                }
        );
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo resolveInfo = (ResolveInfo)l.getAdapter().getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        if(activityInfo == null) return;

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }
}















