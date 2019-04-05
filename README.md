# DigiSign-AuditLog


<p:commandButton id="save" value="#{button.save}" actionListener="#{digiSignDialog.DialogchooseOpen}" immediate="true"  >
							<f:ajax execute="@form" render="@form" />
							<p:ajax event="dialogReturn" listener="#{personEdit.onSignAction}" update=":form" />
</p:commandButton>
