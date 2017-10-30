import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CSUser e2e test', () => {

    let navBarPage: NavBarPage;
    let cSUserDialogPage: CSUserDialogPage;
    let cSUserComponentsPage: CSUserComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CSUsers', () => {
        navBarPage.goToEntity('cs-user-cs');
        cSUserComponentsPage = new CSUserComponentsPage();
        expect(cSUserComponentsPage.getTitle()).toMatch(/costshareApp.cSUser.home.title/);

    });

    it('should load create CSUser dialog', () => {
        cSUserComponentsPage.clickOnCreateButton();
        cSUserDialogPage = new CSUserDialogPage();
        expect(cSUserDialogPage.getModalTitle()).toMatch(/costshareApp.cSUser.home.createOrEditLabel/);
        cSUserDialogPage.close();
    });

    it('should create and save CSUsers', () => {
        cSUserComponentsPage.clickOnCreateButton();
        cSUserDialogPage.setNameInput('name');
        expect(cSUserDialogPage.getNameInput()).toMatch('name');
        cSUserDialogPage.userNameSelectLastOption();
        cSUserDialogPage.paidSelectLastOption();
        cSUserDialogPage.save();
        expect(cSUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CSUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-cs-user-cs div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CSUserDialogPage {
    modalTitle = element(by.css('h4#myCSUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    userNameSelect = element(by.css('select#field_userName'));
    paidSelect = element(by.css('select#field_paid'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    userNameSelectLastOption = function () {
        this.userNameSelect.all(by.tagName('option')).last().click();
    }

    userNameSelectOption = function (option) {
        this.userNameSelect.sendKeys(option);
    }

    getUserNameSelect = function () {
        return this.userNameSelect;
    }

    getUserNameSelectedOption = function () {
        return this.userNameSelect.element(by.css('option:checked')).getText();
    }

    paidSelectLastOption = function () {
        this.paidSelect.all(by.tagName('option')).last().click();
    }

    paidSelectOption = function (option) {
        this.paidSelect.sendKeys(option);
    }

    getPaidSelect = function () {
        return this.paidSelect;
    }

    getPaidSelectedOption = function () {
        return this.paidSelect.element(by.css('option:checked')).getText();
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
