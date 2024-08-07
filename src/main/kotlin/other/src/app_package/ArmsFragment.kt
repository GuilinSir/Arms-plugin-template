package other.src.app_package

import other.ArmsPluginTemplateProviderImpl
import other.commonAnnotation

fun armsFragment(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl) = if (isKt) armsFragmentKt(provider) else armsFragmentJava(provider)

private fun armsFragmentKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.fragmentPackageName.value}
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import ${provider.componentPackageName.value}.Dagger${provider.pageName.value}Component
import ${provider.moudlePackageName.value}.${provider.pageName.value}Module
import ${provider.contractPackageName.value}.${provider.pageName.value}Contract
import ${provider.presenterPackageName.value}.${provider.pageName.value}Presenter
import ${provider.appPackageName.value}.R
import kotlinx.android.synthetic.main.base_title.*

${commonAnnotation(provider)}
class ${provider.pageName.value}Fragment : SimpleBaseFragment<${provider.pageName.value}Presenter>() , ${provider.pageName.value}Contract.View{
    companion object {
    fun newInstance():${provider.pageName.value}Fragment {
        val fragment = ${provider.pageName.value}Fragment()
        return fragment
    }
    }
    override fun setupFragmentComponent(appComponent:AppComponent) {
        Dagger${provider.pageName.value}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .${provider.pageName.value[0].toLowerCase()}${provider.pageName.value.substring(1,provider.pageName.value.length)}Module(${provider.pageName.value}Module(this))
                .build()
                .inject(this)
    }
    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View{
        return inflater.inflate(R.layout.${provider.fragmentLayoutName.value}, container, false)
    }
    /**
     * 在 onActivityCreate()时调用
     */
    override fun initData(savedInstanceState: Bundle?) {
        setToolBarNoBack(toolbar, "${provider.pageName.value}")
        
        initListener()
    }
    
    private fun initListener() {
    
    }
    
    override fun getFragment(): Fragment = this
}
    
"""


fun armsFragmentJava(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.fragmentPackageName.value};
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import android.content.Intent;
import com.jess.arms.utils.ArmsUtils;
import ${provider.componentPackageName.value}.Dagger${provider.pageName.value}Component;
import ${provider.moudlePackageName.value}.${provider.pageName.value}Module;
import ${provider.contractPackageName.value}.${provider.pageName.value}Contract;
import ${provider.presenterPackageName.value}.${provider.pageName.value}Presenter;
import ${provider.appPackageName.value}.R;

${commonAnnotation(provider)}
public class ${provider.pageName.value}Fragment extends BaseFragment<${provider.pageName.value}Presenter> implements ${provider.pageName.value}Contract.View{
    
    public static ${provider.pageName.value}Fragment newInstance() {
        ${provider.pageName.value}Fragment fragment = new ${provider.pageName.value}Fragment();
        return fragment;
    }
    
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        Dagger${provider.pageName.value}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                 .${provider.pageName.value[0].toLowerCase()}${provider.pageName.value.substring(1, provider.pageName.value.length)}Module(new ${provider.pageName.value}Module(this))
                .build()
                .inject(this);
    }
    
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.${provider.fragmentLayoutName.value}, container, false);
    }
    
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    
    }
    
        @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }
}
    
"""