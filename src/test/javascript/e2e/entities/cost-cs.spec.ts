import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Cost e2e test', () => {

    let navBarPage: NavBarPage;
    let costDialogPage: CostDialogPage;
    let costComponentsPage: CostComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Costs', () => {
        navBarPage.goToEntity('cost-cs');
        costComponentsPage = new CostComponentsPage();
        expect(costComponentsPage.getTitle()).toMatch(/costshareApp.cost.home.title/);

    });

    it('should load create Cost dialog', () => {
        costComponentsPage.clickOnCreateButton();
        costDialogPage = new CostDialogPage();
        expect(costDialogPage.getModalTitle()).toMatch(/costshareApp.cost.home.createOrEditLabel/);
        costDialogPage.close();
    });

    it('should create and save Costs', () => {
        costComponentsPage.clickOnCreateButton();
        costDialogPage.setNameInput('name');
        expect(costDialogPage.getNameInput()).toMatch('name');
        costDialogPage.setDescriptionInput('description');
        expect(costDialogPage.getDescriptionInput()).toMatch('description');
        costDialogPage.setSumInput('5');
        expect(costDialogPage.getSumInput()).toMatch('5');
        costDialogPage.paidBySelectLastOption();
        costDialogPage.groupSelectLastOption();
        costDialogPage.save();
        expect(costDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CostComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-cost-cs div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CostDialogPage {
    modalTitle = element(by.css('h4#myCostLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    sumInput = element(by.css('input#field_sum'));
    paidBySelect = element(by.css('select#field_paidBy'));
    groupSelect = element(by.css('select#field_group'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setSumInput = function (sum) {
        this.sumInput.sendKeys(sum);
    }

    getSumInput = function () {
        return this.sumInput.getAttribute('value');
    }

    paidBySelectLastOption = function () {
        this.paidBySelect.all(by.tagName('option')).last().click();
    }

    paidBySelectOption = function (option) {
        this.paidBySelect.sendKeys(option);
    }

    getPaidBySelect = function () {
        return this.paidBySelect;
    }

    getPaidBySelectedOption = function () {
        return this.paidBySelect.element(by.css('option:checked')).getText();
    }

    groupSelectLastOption = function () {
        this.groupSelect.all(by.tagName('option')).last().click();
    }

    groupSelectOption = function (option) {
        this.groupSelect.sendKeys(option);
    }

    getGroupSelect = function () {
        return this.groupSelect;
    }

    getGroupSelectedOption = function () {
        return this.groupSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
