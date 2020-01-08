package com.song.tasty.common.app.music


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.song.tasty.common.app.R
import com.song.tasty.common.app.music.bean.MusicBean

/**
 * @date : 2020-01-07 11:25
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :音乐播放器通知栏
 * 关闭通知栏通知  https://www.jianshu.com/p/4df783c4e80d
 */


/**internal:在同一模块内使用  模块(module)是指一起编译的一组 Kotlin 源代码文件
 * 在java文件中使用getMUSIC_NOTIFICATION_ACTION_CLOSE()调用
 */


internal val MUSIC_NOTIFICATION_ACTION_PRE = "MUSIC_NOTIFICATION_ACTION_PRE"
internal val MUSIC_NOTIFICATION_ACTION_NEXT = "MUSIC_NOTIFICATION_ACTION_NEXT"
internal val MUSIC_NOTIFICATION_ACTION_PLAY = "MUSIC_NOTIFICATION_ACTION_PLAY"
internal val MUSIC_NOTIFICATION_ACTION_CLOSE = "MUSIC_NOTIFICATION_ACTION_CLOSE"

open class MusicNotification(private val service: MusicService) {


    //val 不可变  var 可变
    private val CHANNEL_ID = "chanel_play_music"
    private val NOTIFICATION_ID = 0x1234

    private val context: Context = service.applicationContext
    private var notification: Notification? = null;

    private val preIntent: Intent by lazy {
        Intent().apply {
            action = MUSIC_NOTIFICATION_ACTION_PRE
        }
    }

    private val nextIntent: Intent by lazy {
        Intent().apply {
            action = MUSIC_NOTIFICATION_ACTION_NEXT
        }
    }

    private val playIntent: Intent by lazy {
        Intent().apply {
            action = MUSIC_NOTIFICATION_ACTION_PLAY
        }
    }

    private val closeIntent: Intent by lazy {
        Intent().apply {
            action = MUSIC_NOTIFICATION_ACTION_CLOSE
        }
    }

    private val remoteView: RemoteViews by lazy {
        RemoteViews(context.packageName, R.layout.layout_notification_music)
    }


    fun stopNotification() {
        service.stopForeground(true)
        notification = null
    }


    fun pause() {
        notification?.let {
            remoteView.setImageViewResource(R.id.ivPlay, R.mipmap.ic_play)
            service.startForeground(NOTIFICATION_ID, notification)
        }
    }

    fun resume() {
        notification?.let {
            remoteView.setImageViewResource(R.id.ivPlay, R.mipmap.ic_pause)
            service.startForeground(NOTIFICATION_ID, notification)
        }
    }


    fun startNotification(musicBean: MusicBean) {
        remoteView.setImageViewResource(R.id.ivPlay, R.mipmap.ic_pause)
        remoteView.setTextViewText(R.id.tvName, musicBean.name)

        if (notification == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val appName = context.getString(R.string.app_name)

                //NotificationManager.IMPORTANCE_MIN: 静默;
                //NotificationManager.IMPORTANCE_HIGH:随系统使用声音或振动
                NotificationChannel(CHANNEL_ID, appName, NotificationManager.IMPORTANCE_MIN).apply {
                    description = appName
                    val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    manager.createNotificationChannel(this)
                }
            }

            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_logo)
                    .setTicker("音频播放啦~~")
                    .setVibrate(null)
                    .setLights(0, 0, 0)
                    .setWhen(System.currentTimeMillis())
                    .setContent(remoteView)//设置普通notification视图
//                    .setCustomBigContentView(bigView)//设置显示bigView的notification视图
//                    .setPriority(NotificationCompat.PRIORITY_MAX)//设置最大优先级
//                    .setOngoing(true) //true使notification变为ongoing，用户不能手动清除，类似QQ,false或者不设置则为普通的通知
                    .build()



            remoteView.setOnClickPendingIntent(R.id.ivPre, PendingIntent.getBroadcast(context,
                    0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            remoteView.setOnClickPendingIntent(R.id.ivPlay, PendingIntent.getBroadcast(context,
                    0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            remoteView.setOnClickPendingIntent(R.id.ivNext, PendingIntent.getBroadcast(context,
                    0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT))
            remoteView.setOnClickPendingIntent(R.id.ivClose, PendingIntent.getBroadcast(context,
                    0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT))

        }

//        val activityIntent = Intent(context, PlayerHandleActivity::class.java).apply {
//            data = Uri.parse("${BuildConfig.APP_SCHEME}://goto_play_audio/${musicBean.toJson()}")
//        }
//        remoteView.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(context,
//                0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT))

        service.startForeground(NOTIFICATION_ID, notification)
        musicBean.cover?.let {
            loadBitmap(it)
        }


    }

    private fun loadBitmap(url: String) {
        val wh = imgWH()
        val target = object : CustomTarget<Bitmap>(wh, wh) {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                notification?.let {
                    remoteView.setBitmap(R.id.ivClose, "setImageBitmap", resource)
                    service.startForeground(NOTIFICATION_ID, notification)
                }
            }
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(target)
    }


    private fun imgWH(): Int = (64 * context.resources.displayMetrics.density).toInt()
}