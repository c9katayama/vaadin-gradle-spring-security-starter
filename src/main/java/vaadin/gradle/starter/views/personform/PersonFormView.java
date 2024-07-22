package vaadin.gradle.starter.views.personform;

import java.util.Optional;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import vaadin.gradle.starter.data.SamplePerson;
import vaadin.gradle.starter.data.SamplePersonRepository;
import vaadin.gradle.starter.views.MainLayout;

@PageTitle("Person Form")
@Route(value = "person-form", layout = MainLayout.class)
@RolesAllowed("USER")
public class PersonFormView extends VerticalLayout {

	private TextField firstNameTextField = new TextField("First Name");
	private TextField lastNameTextField = new TextField("Last Name");
	private EmailField emailTextField = new EmailField("Email");
	private TextField phoneTextField = new TextField("Phone Number");
	private DatePicker dateOfBirthDatePicker = new DatePicker("Birthday");
	private TextField occupationTextField = new TextField("Occupation");
	private TextField roleTextField = new TextField("Role");

	private Button saveButton = new Button("Save");
	private Button clearButton = new Button("Clear");

	private Binder<SamplePerson> binder = new Binder<SamplePerson>(SamplePerson.class);

	private SamplePersonRepository samplePersonRepository;

	/**
	 * @param samplePersonRepository SpringによってDIされる
	 */
	public PersonFormView(SamplePersonRepository samplePersonRepository) {
		System.out.println("PersonFormView is created.");

		this.samplePersonRepository = samplePersonRepository;
		// 全体を中央寄せ
		setAlignItems(Alignment.CENTER);
		// 外枠
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setMaxWidth("800px");
		mainLayout.setWidth("100%");

		// ヘッダー部分
		H3 h3 = new H3("Personal Information");
		h3.setWidth("100%");

		// ボタン部分
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setWidth("100%");
		saveButton.addClickListener(e -> {
			save();
		});
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		clearButton.addClickListener(e -> {
			binder.setBean(null);
		});
		buttonsLayout.add(saveButton, clearButton);

		// フォーム
		FormLayout formLayout = new FormLayout();
		formLayout.setWidth("100%");
		formLayout.add(firstNameTextField, lastNameTextField, dateOfBirthDatePicker, phoneTextField, emailTextField,
				occupationTextField, roleTextField, buttonsLayout);
		// ヘッダーとフォームをメインコンテンツに追加
		mainLayout.add(h3, formLayout);
		// メインコンテンツをViewに追加
		add(mainLayout);
		// DBを検索
		Optional<SamplePerson> savedPerson = samplePersonRepository.findById(1L);
		if (savedPerson.isPresent()) {
			// binderにデータをセットすると、フォームに値が入る
			binder.setBean(savedPerson.get());
		}

		// Binderに入力フィールド、バリデーション、バインド先プロパティ名を設定
		binder.forField(firstNameTextField).asRequired("first name is required").bind("firstName");
		binder.forField(lastNameTextField).asRequired("last name is required").bind("lastName");
		// バインド先プロパティ名の代わりに、setter/getterでの設定も可能
		binder.forField(dateOfBirthDatePicker).asRequired("Birthday is required").bind(SamplePerson::getDateOfBirth,
				SamplePerson::setDateOfBirth);
		binder.forField(emailTextField).asRequired("Email is required")
				.withValidator(new EmailValidator("Invalid Email format"))
				.bind(SamplePerson::getEmail, SamplePerson::setEmail);
		binder.forField(phoneTextField).bind(SamplePerson::getPhone, SamplePerson::setPhone);
		binder.forField(dateOfBirthDatePicker).asRequired("Birthday is required").bind(SamplePerson::getDateOfBirth,
				SamplePerson::setDateOfBirth);
		binder.forField(occupationTextField).bind(SamplePerson::getOccupation, SamplePerson::setOccupation);
		binder.forField(roleTextField).bind(SamplePerson::getRole, SamplePerson::setRole);

		// 入力変更時にボタンのステートを変更
		binder.addStatusChangeListener(e -> {
			updateButtonState();
		});
	}

	private void updateButtonState() {
		saveButton.setEnabled(binder.isValid());
	}

	private void save() {
		if (binder.validate().isOk()) {
			samplePersonRepository.save(binder.getBean());
			// メッセージダイアログ
			ConfirmDialog dialog = new ConfirmDialog();
			dialog.setText("Updated.");
			dialog.setConfirmText("OK");
			dialog.open();
		}
	}
}
