package com.example.widget12

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.SizeF
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.BuildCompat

const val TAG = "TodoWidget"

const val EXTRA_VIEW_ID = "extra_view_id"

class TodoWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d(TAG, "TodoWidget onReceive: ")

    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d(TAG, "onAppWidgetOptionsChanged: 组件大小发生变化")
    }

    override fun onEnabled(context: Context) {
        /*首次创建组件*/
        Log.d(TAG, "onEnabled")
    }

    override fun onDisabled(context: Context) {
        /*最后一个组件被删除*/
        Log.d(TAG, "onDisabled")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "onDeleted")
    }
}

@SuppressLint("RemoteViewLayout")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

/*    val views = RemoteViews(context.packageName, R.layout.todo_widget)
    views.setTextViewText(R.id.xxx,"this is TODO list")
    appWidgetManager.updateAppWidget(appWidgetId, views)*/



    val remoteViewsMin = RemoteViews(
        context.packageName,
        R.layout.todo_widget_min
    )

    val remoteViewsNormal = RemoteViews(
        context.packageName,
        R.layout.todo_widget_normal
    )

    val remoteViewsMax = RemoteViews(
        context.packageName,
        R.layout.todo_widget_max
    ).apply {
        //打开App
        setOnClickPendingIntent(R.id.add, openAppPendingIntent(context,R.id.add))
        //setOnClickPendingIntent(R.id.duck_debug, openAppPendingIntent(context,R.id.duck_debug))

        setOnClickPendingIntent(R.id.title, receivePendingIntent(context,R.id.title))
    }

    /*不同大小控件对应不同布局*/
    val viewMapping: MutableMap<SizeF, RemoteViews> = mutableMapOf()
    viewMapping[SizeF(180.0f, 110.0f)] = remoteViewsMin
    viewMapping[SizeF(230.0f, 180.0f)] = remoteViewsNormal
    viewMapping[SizeF(270.0f, 300.0f)] = remoteViewsMax

    /*只有在Android12以上版本才支持*/
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        appWidgetManager.updateAppWidget(appWidgetId, RemoteViews(viewMapping))
    }else{
        appWidgetManager.updateAppWidget(appWidgetId, remoteViewsNormal)
    }

}

private fun openAppPendingIntent(context: Context,@IdRes id:Int):PendingIntent{
    /*打开APP intent*/
    val activityIntent = Intent(context, MainActivity::class.java).apply {
        setData(Uri.parse("harvic:$id"))
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    val appOpenIntent = PendingIntent.getActivity(
        context,
        1,
        activityIntent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    return appOpenIntent
}

private fun receivePendingIntent(context: Context,@IdRes id:Int):PendingIntent{
    val activityIntent = Intent(context, TodoWidget::class.java).apply {
        setData(Uri.parse("harvic:$id"))
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    return PendingIntent.getActivity(
        context,
        2,
        activityIntent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}
