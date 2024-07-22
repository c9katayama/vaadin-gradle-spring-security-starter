package vaadin.gradle.starter.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import vaadin.gradle.starter.views.MainLayout;

@PageTitle("Hello World")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class HelloWorldView extends HorizontalLayout {

	private TextField name;
	private Button sayHello;

	public HelloWorldView() {
		name = new TextField("Your name");
		sayHello = new Button("Say hello");
		// クリック時イベント
		sayHello.addClickListener(e -> {
			Notification.show("Hello " + name.getValue());
		});
		// Enterキー押下でクリック
		sayHello.addClickShortcut(Key.ENTER);
		setMargin(true);
		// コンポーネントをした寄せする
		setVerticalComponentAlignment(Alignment.END, name, sayHello);
		// コンポーネントを画面に追加
		add(name, sayHello);
	}

}
