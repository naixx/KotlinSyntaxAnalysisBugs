package me.packbag.android.ui.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;

import java.util.List;

import me.packbag.android.R;
import me.packbag.android.db.model.ItemSet;

/**
 * Created by astra on 03.10.2015.
 */
public class ShareHelper {

    public static final int REQUEST_CODE = 44444;

    private void initShareIntent(final Context context, String targetPackage, Uri imageUri) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(targetPackage) ||
                    info.activityInfo.name.toLowerCase().contains(targetPackage)) {
                    if (info.activityInfo.name.toLowerCase().contains("messenger")) {
                        continue;
                    }
                    Log.i("tag", "_info_name_" + info.activityInfo.name);
                    share.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    share.putExtra(Intent.EXTRA_TEXT, "your text");
                    share.putExtra(Intent.EXTRA_STREAM, imageUri);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return;
            }

            context.startActivity(Intent.createChooser(share, "Select"));
        }
    }

    public static void shareCommon(final Activity context) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_text_common));
        share.setType("text/plain");
        context.startActivityForResult(share, REQUEST_CODE);
    }

    public static void onActivityResultForCommon(
        final Context context, int requestCode, int resultCode, Intent data, ItemSet itemSet) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ComponentName componentName = data.getComponent();
            final String packageName = componentName.getPackageName();
        }
    }

    public static ShareContent prepareFbShareContent(final Context context) {
        return new ShareLinkContent.Builder() //
            .setContentUrl(Uri.parse("http://hikapro.com"))
            .setContentTitle(context.getString(R.string.share_text_fb_title))
            .build();
    }
}
