package com.packagename.myapp.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

	private CustomerService service = CustomerService.getInstance();
	private Grid<Customer> grid = new Grid<>(Customer.class);

	public MainView(@Autowired MessageBean bean) {

		DataProvider<Customer, Void> dataProvider = DataProvider.fromCallbacks(
				// First callback fetches items based on a query
				query -> {
					// The index of the first item to load
					int offset = query.getOffset();

					// The number of items to load
					int limit = query.getLimit();

					System.out.println(MessageFormat.format("offset: [{0}], limit: [{1}]", offset, limit));

					List<Customer> customers = service.findAll(null, offset, limit);

					return customers.stream();
				},
				// Second callback fetches the number of items for a query
				query -> service.count());
		
		grid.setColumns("id", "firstName", "lastName", "status");
		grid.setDataProvider(dataProvider);
		add(grid);
		setSizeFull();
	}

}
