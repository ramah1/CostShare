import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CSGroup e2e test', () => {

    let navBarPage: NavBarPage;
    let cSGroupDialogPage: CSGroupDialogPage;
    let cSGroupComponentsPage: CSGroupComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CSGroups', () => {
        navBarPage.goToEntity('cs-group-cs');
        cSGroupComponentsPage = new CSGroupComponentsPage();
        expect(cSGroupComponentsPage.getTitle()).toMatch(/costshareApp.cSGroup.home.title/);

    });

    it('should load create CSGroup dialog', () => {
        cSGroupComponentsPage.clickOnCreateButton();
        cSGroupDialogPage = new CSGroupDialogPage();
        expect(cSGroupDialogPage.getModalTitle()).toMatch(/costshareApp.cSGroup.home.createOrEditLabel/);
        cSGroupDialogPage.close();
    });

    it('should create and save CSGroups', () => {
        cSGroupComponentsPage.clickOnCreateButton();
        cSGroupDialogPage.setNameInput('name');
        expect(cSGroupDialogPage.getNameInput()).toMatch('name');
        cSGroupDialogPage.setDescriptionInput('description');
        expect(cSGroupDialogPage.getDescriptionInput()).toMatch('description');
        // cSGroupDialogPage.membersSelectLastOption();
        // cSGroupDialogPage.adminsSelectLastOption();
        cSGroupDialogPage.save();
        expect(cSGroupDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CSGroupComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-cs-group-cs div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CSGroupDialogPage {
    modalTitle = element(by.css('h4#myCSGroupLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    membersSelect = element(by.css('select#field_members'));
    adminsSelect = element(by.css('select#field_admins'));

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

    membersSelectLastOption = function () {
        this.membersSelect.all(by.tagName('option')).last().click();
    }

    membersSelectOption = function (option) {
        this.membersSelect.sendKeys(option);
    }

    getMembersSelect = function () {
        return this.membersSelect;
    }

    getMembersSelectedOption = function () {
        return this.membersSelect.element(by.css('option:checked')).getText();
    }

    adminsSelectLastOption = function () {
        this.adminsSelect.all(by.tagName('option')).last().click();
    }

    adminsSelectOption = function (option) {
        this.adminsSelect.sendKeys(option);
    }

    getAdminsSelect = function () {
        return this.adminsSelect;
    }

    getAdminsSelectedOption = function () {
        return this.adminsSelect.element(by.css('option:checked')).getText();
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
