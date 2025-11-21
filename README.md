# ronny-tabango-pruebatecnica
## 🧪 Prueba Técnica – Spring Boot + Azure Pipelines (CI/CD)

Este repositorio contiene el proyecto principal de backend desarrollado en **Spring Boot**, junto con la integración al pipeline de CI/CD en **Azure DevOps**.
El proyecto está organizado en **dos repositorios independientes**, cada uno con un propósito claro dentro del flujo de desarrollo y despliegue.

---

## 📁 **Estructura del Proyecto**

### 🔹 1. **Backend – Aplicación Spring Boot**

📦 **Repositorio:** `pruebatecnica-springboot-ronny`
Este repositorio contiene la aplicación Java Spring Boot llamada:

```
crud-simple-ronny
```

Es un CRUD básico sin base de datos, implementado como parte de la prueba técnica, y actualmente se despliega usando CI/CD en Azure Pipelines.

---

### 🔹 2. **Repositorio de Plantillas CI/CD**

📦 **Repositorio:** `pruebatecnica-ronny-devops`

Este repositorio contiene **plantillas de Azure Pipelines** reutilizables, dentro de la carpeta:

```
azure/templates/
```

➡️ Aquí se encuentra el archivo:

```
SpringBootServiceTemplate.yml
```

Este template centraliza la lógica del pipeline e incluye tareas como:

* Compilación del proyecto
* Análisis SonarQube
* Generación del JAR
* Publicación en Artifacts
* Copiado y despliegue automático en un servidor remoto Linux vía SSH
* Health check vía HTTP
* Manejo de backups del JAR

---

## 🚀 **Integración de Repositorios en Azure DevOps**

Ambos repositorios son independientes, pero el pipeline del backend importa las plantillas desde el repositorio DevOps mediante `resources.repositories`.

A continuación se muestra la configuración del archivo `azure-pipelines.yml` ubicado en:

📂 `pruebatecnica-springboot-ronny/azure-pipelines.yml`

```yaml
parameters:
- name: PATH_PROJECT
  default: 'crud-simple-ronny'
- name: PROJECT_KEY_SONAR
  default: 'pruebatecnica-springboot-ronny'

trigger:
- main
- develop

resources:
  repositories:
    - repository: templates
      type: git
      name: pruebatecnica-ronny-devops
      ref: refs/tags/release-1.0.0

stages:
  - template: azure/templates/SpringBootServiceTemplate.yml@templates
    parameters:
      PATH_PROJECT: ${{ parameters.PATH_PROJECT }}
      PROJECT_KEY_SONAR: ${{ parameters.PROJECT_KEY_SONAR }}
      GROUP_ID: 'com.pruebatecnica'
      ARTIFACT_ID: 'crud-simple-ronny'
      VERSION: '2.0.0-SNAPSHOT'
```

---

## ⚙️ **Flujo General del Pipeline**

1. **Checkout del repositorio del backend**
2. **Importación del repositorio de templates**
3. **Construcción del proyecto con Maven**
4. **Análisis de calidad con SonarQube**
5. **Generación del artefacto `.jar`**
6. **Publicación en Azure Artifacts**
7. **Conexión SSH a servidor remoto**
8. **Copia del nuevo JAR a `/tmp`**
9. **Ejecución del script `updated_jar.sh`**
10. **Health check vía `/actuator/health`**

---

## 🧩 **Objetivo del Proyecto**

Este enfoque permite:

✔ Separar CI/CD del código fuente
✔ Reutilizar plantillas en múltiples repositorios
✔ Despliegues automatizados y controlados
✔ Versionado de templates con tags (`release-1.0.0`)
✔ Mantenimiento más simple y escalable
