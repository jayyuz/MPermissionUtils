/**
 * @author Jaesoon
 * @version 创建时间：2016年10月18日 下午3:11:49 
 */


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * @author Jaesoon
 *
 */
public class PermissionUtils {

	private static final int REQUEST_WRITE_READ = 100;
	private static final int REQUEST_LOCATION = 101;
	private static final int REQUEST_CAMERA = 102;
	private static final int REQUEST_PHONE_STATE = 103;
	private static final int REQUEST_READ_SMS = 104;

	private PermissionListener listener = null;

	/**
	 * 请求读写文件权限（图片缓存用到）
	 */
	public void requestStoragePermission(final Fragment fragment, final PermissionListener listener) {

		this.listener = listener;

		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(fragment.getContext(),
					Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
				listener.onSucced();
				return;
			}
		} catch (RuntimeException e) {
			Toast.makeText(fragment.getContext(), "为了正常使用，请开启应用文件读写权限", Toast.LENGTH_SHORT).show();
			listener.onFailed();
			return;
		}

		if (checkSelfPermission != PackageManager.PERMISSION_GRANTED
				&& (fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
						|| fragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
			LightDialog dialog = LightDialog.create(fragment.getActivity());
			dialog.setTitle("温馨提示");
			dialog.setMessage("当前操作需要使用写文件权限，请许可！");
			dialog.setPositiveButton(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					fragment.requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
							REQUEST_WRITE_READ);
					fragment.requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
							REQUEST_WRITE_READ);
				}
			});
			dialog.show();
		} else {
			listener.onSucced();
		}
	}

	/**
	 * 请求读写文件权限（图片缓存用到）
	 */
	public void requestStoragePermission(final Activity activity, final PermissionListener listener) {

		this.listener = listener;
		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(activity,
					Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
				listener.onSucced();
				return;
			}
		} catch (RuntimeException e) {
			 //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
	        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission()则可能会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)
			Toast.makeText(activity, "为了正常使用，请开启应用文件读写权限", Toast.LENGTH_SHORT).show();
			listener.onFailed();
			return;
		}

		if (ActivityCompat
				.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
				|| ActivityCompat.shouldShowRequestPermissionRationale(activity,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			//用戶拒绝过权限，再次申请 
			LightDialog dialog = LightDialog.create(activity);
			dialog.setTitle("温馨提示");
			dialog.setMessage("当前操作需要使用写文件权限，请许可！");
			dialog.setPositiveButton(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityCompat.requestPermissions(activity,
							new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_WRITE_READ);
					ActivityCompat.requestPermissions(activity,
							new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, REQUEST_WRITE_READ);
				}
			});
			dialog.show();
		} else {
			//第一次
			ActivityCompat.requestPermissions(activity,
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_WRITE_READ);
			ActivityCompat.requestPermissions(activity,
					new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, REQUEST_WRITE_READ);
		}
	}

	/**
	 * 请求定位权限
	 */
	public void requestLocationPermission(final Fragment fragment, final PermissionListener listener) {
		this.listener = listener;
		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(fragment.getContext(),
					Manifest.permission.ACCESS_COARSE_LOCATION);
			if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
				listener.onSucced();
				return;
			}
		} catch (RuntimeException e) {
			listener.onFailed();
			return;
		}

		if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
						|| fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
			LightDialog dialog = LightDialog.create(fragment.getActivity());
			dialog.setTitle("温馨提示");
			dialog.setMessage("当前操作需要使用定位权限，请许可！");
			dialog.setPositiveButton(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					fragment.requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
							REQUEST_LOCATION);
					fragment.requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
							REQUEST_LOCATION);
				}
			});
			dialog.show();
		} else {
			listener.onSucced();
		}
	}

	/**
	 * Requests the Camera permission. If the permission has been denied
	 * previously, a SnackBar will prompt the user to grant the permission,
	 * otherwise it is requested directly.
	 */
	public void requestCameraPermission(final Activity activity, final PermissionListener listener) {
		this.listener = listener;
		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(activity,
					Manifest.permission.CAMERA);
			if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
				listener.onSucced();
				return;
			}
		} catch (RuntimeException e) {
			listener.onFailed();
			return;
		}
		if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
			LightDialog dialog = LightDialog.create(activity);
			dialog.setTitle("温馨提示");
			dialog.setMessage("当前操作需要使用摄像头，请许可权限！");
			dialog.setPositiveButton(new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.CAMERA },
							REQUEST_CAMERA);
				}
			});
			dialog.show();
		} else {
			// Camera permission has not been granted yet. Request it directly.
			ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.CAMERA }, REQUEST_CAMERA);
		}
	}

	public void requestPhoneStatePermission(final Activity activity, final PermissionListener listener) {
		this.listener = listener;
		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(activity,
					Manifest.permission.READ_PHONE_STATE);
			if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
				listener.onSucced();
				return;
			}
		} catch (RuntimeException e) {
			listener.onFailed();
			return;
		}
		if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
			LightDialog dialog = LightDialog.create(activity);
			dialog.setTitle("温馨提示");
			dialog.setMessage("当前操作需要使用获取设备号，用以提高账户安全性，请许可权限！");
			dialog.setPositiveButton(new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_PHONE_STATE },
							REQUEST_PHONE_STATE);
				}
			});
			dialog.show();
		} else {
			ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_PHONE_STATE },
					REQUEST_PHONE_STATE);
		}

	}

	/** 读取短信权限申请 */
	public void requestSmsPermission(final Activity activity, final PermissionListener listener) {
		this.listener = listener;
		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(activity,
					Manifest.permission.READ_SMS);
			if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
				listener.onSucced();
				return;
			}
		} catch (RuntimeException e) {
			listener.onFailed();
			return;
		}
		if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
			LightDialog dialog = LightDialog.create(activity);
			dialog.setTitle("温馨提示");
			dialog.setMessage("为了方便您，我们会读取短信以自动填写验证码，请许可短信读取权限！");
			dialog.setPositiveButton(new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_SMS },
							REQUEST_READ_SMS);
				}
			});
			dialog.show();
		} else {
			ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.READ_SMS },
					REQUEST_READ_SMS);
		}
	}

	/** 使用时请注意查看此部分源代码 */
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (listener != null) {
			if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				listener.onSucced();
			} else {
				listener.onFailed();
			}
		}
	}

}
