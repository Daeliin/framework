function printresourcemodel() {
	packagePath=${1//\//.} 
	import1="import com.daeliin.framework.commons.model.LongIdPersistentResource;"
	import2="import javax.persistence.Entity;"

	echo -e "package $packagePath.model;\n\n$import1\n$import2\n\n@Entity\npublic class $2 extends LongIdPersistentResource {\n\npublic $2(){\n}\n}"
}

function printresourcerepository() {
	packagePath=${1//\//.} 
	import1="import com.daeliin.framework.core.repository.ResourceRepository;"
	import2="import $packagePath.model.$2;"
	import3="import org.springframework.stereotype.Repository;"

	echo  -e "package $packagePath.repository;\n\n$import1\n$import2\n$import3\n\n@Repository\npublic interface $2Repository extends ResourceRepository<$2, Long> {\n}"
}

function printresourceservice() {
	packagePath=${1//\//.} 
	import1="import com.daeliin.framework.core.service.ResourceService;"
	import2="import $packagePath.model.$2;"
	import3="import $packagePath.repository.$2Repository;"
	import4="import org.springframework.stereotype.Service;"

	echo  -e "package $packagePath.service;\n\n$import1\n$import2\n$import3\n$import4\n\n@Service\npublic class $2Service extends ResourceService<$2, Long, $2Repository> {\n}"
}

function printresourcecontroller() {
	packagePath=${1//\//.} 
	import1="import com.daeliin.framework.core.controller.ResourceController;"
	import2="import $packagePath.model.$2;"
	import3="import $packagePath.service.$2Service;"
	import4="import org.springframework.web.bind.annotation.RequestMapping;"
	import5="import org.springframework.web.bind.annotation.RestController;"

	echo  -e "package $packagePath.controller;\n\n$import1\n$import2\n$import3\n$import4\n$import5\n\n@RestController\n@RequestMapping(API_ROOT_PATH + \"${2,,}s\")\npublic class $2Controller extends ResourceController<$2, Long, $2Service> {\n}"
}

function create_resource_api() {
	resourceuppercasefirstletter=${1##*'/'} 
	resourceuppercasefirstletter=${resourceuppercasefirstletter^}
    mkdir -p $1
    mkdir $1/model
    mkdir $1/repository
    mkdir $1/service
    mkdir $1/controller
    printresourcemodel $1 $resourceuppercasefirstletter > $1/model/${resourceuppercasefirstletter}.java
    printresourcerepository $1 $resourceuppercasefirstletter > $1/repository/${resourceuppercasefirstletter}Repository.java
    printresourceservice $1 $resourceuppercasefirstletter > $1/service/${resourceuppercasefirstletter}Service.java
    printresourcecontroller $1 $resourceuppercasefirstletter > $1/controller/${resourceuppercasefirstletter}Controller.java
}

create_resource_api $1