
package wease_one.mub.com;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mub.wease.wease_one.R;
import com.mub.wease.wease_one.UI.OptionsEPSPActivity;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
       // appWidgetManager.updateAppWidget(appWidgetId, views);
        /** PendingIntent to launch the MainActivity when the widget was clicked **/
        Intent launchMain = new Intent(context, OptionsEPSPActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingMainIntent);
        views.setOnClickPendingIntent(R.id.appwidget_text2, pendingMainIntent);
        views.setOnClickPendingIntent(R.id.appwidgetImage, pendingMainIntent);
        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_listView);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

