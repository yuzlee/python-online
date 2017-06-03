//#include <Python.h>
//#include <iostream>
//#include <string>
//
//int main() {
//	Py_Initialize();
//	if (!Py_IsInitialized()) {
//		std::cerr << "Error while initializing python environment." << std::endl;
//	}
//	std::cout << "Python Initialized." << std::endl;
//	while (true) {
//		std::string code;
//		getline(std::cin, code);
//		PyRun_SimpleString(code.c_str());
//		if (PyErr_Occurred()) {
//			PyErr_Print();
//		}
//		
//		std::cout << "--------" << std::endl;
//		std::cout << stdout;
//		std::cout << "++++++++" << std::endl;
//		std::cout.flush();
//	}
//	Py_Finalize();
//	return 0;
//}


#include <functional>
#include <iostream>
#include <string>
#include <Python.h>

namespace emb {

	typedef std::function<void(std::string)> stdout_write_type;

	struct Stdout {
		PyObject_HEAD
			stdout_write_type write;
	};

	PyObject* Stdout_write(PyObject* self, PyObject* args) {
		std::size_t written(0);
		Stdout* selfimpl = reinterpret_cast<Stdout*>(self);
		if (selfimpl->write) {
			char* data;
			if (!PyArg_ParseTuple(args, "s", &data))
				return 0;

			std::string str(data);
			selfimpl->write(str);
			written = str.size();
		}
		return PyLong_FromSize_t(written);
	}

	PyObject* Stdout_flush(PyObject* self, PyObject* args) {
		// no-op
		return Py_BuildValue("");
	}

	PyMethodDef Stdout_methods[] =
	{
		{ "write", Stdout_write, METH_VARARGS, "sys.stdout.write" },
		{ "flush", Stdout_flush, METH_VARARGS, "sys.stdout.flush" },
		{ 0, 0, 0, 0 } // sentinel
	};

	PyMethodDef Stderr_methods[] =
	{
		{ "write", Stdout_write, METH_VARARGS, "sys.stderr.write" },
		{ "flush", Stdout_flush, METH_VARARGS, "sys.stderr.flush" },
		{ 0, 0, 0, 0 } // sentinel
	};

	PyTypeObject StdoutType =
	{
		PyVarObject_HEAD_INIT(0, 0)
		"emb.StdoutType",     /* tp_name */
		sizeof(Stdout),       /* tp_basicsize */
		0,                    /* tp_itemsize */
		0,                    /* tp_dealloc */
		0,                    /* tp_print */
		0,                    /* tp_getattr */
		0,                    /* tp_setattr */
		0,                    /* tp_reserved */
		0,                    /* tp_repr */
		0,                    /* tp_as_number */
		0,                    /* tp_as_sequence */
		0,                    /* tp_as_mapping */
		0,                    /* tp_hash  */
		0,                    /* tp_call */
		0,                    /* tp_str */
		0,                    /* tp_getattro */
		0,                    /* tp_setattro */
		0,                    /* tp_as_buffer */
		Py_TPFLAGS_DEFAULT,   /* tp_flags */
		"emb.Stdout objects", /* tp_doc */
		0,                    /* tp_traverse */
		0,                    /* tp_clear */
		0,                    /* tp_richcompare */
		0,                    /* tp_weaklistoffset */
		0,                    /* tp_iter */
		0,                    /* tp_iternext */
		Stdout_methods,       /* tp_methods */
		0,                    /* tp_members */
		0,                    /* tp_getset */
		0,                    /* tp_base */
		0,                    /* tp_dict */
		0,                    /* tp_descr_get */
		0,                    /* tp_descr_set */
		0,                    /* tp_dictoffset */
		0,                    /* tp_init */
		0,                    /* tp_alloc */
		0,                    /* tp_new */
	};

	PyTypeObject StderrType =
	{
		PyVarObject_HEAD_INIT(0, 0)
		"emb.StderrType",     /* tp_name */
		sizeof(Stdout),       /* tp_basicsize */
		0,                    /* tp_itemsize */
		0,                    /* tp_dealloc */
		0,                    /* tp_print */
		0,                    /* tp_getattr */
		0,                    /* tp_setattr */
		0,                    /* tp_reserved */
		0,                    /* tp_repr */
		0,                    /* tp_as_number */
		0,                    /* tp_as_sequence */
		0,                    /* tp_as_mapping */
		0,                    /* tp_hash  */
		0,                    /* tp_call */
		0,                    /* tp_str */
		0,                    /* tp_getattro */
		0,                    /* tp_setattro */
		0,                    /* tp_as_buffer */
		Py_TPFLAGS_DEFAULT,   /* tp_flags */
		"emb.Stderr objects", /* tp_doc */
		0,                    /* tp_traverse */
		0,                    /* tp_clear */
		0,                    /* tp_richcompare */
		0,                    /* tp_weaklistoffset */
		0,                    /* tp_iter */
		0,                    /* tp_iternext */
		Stderr_methods,       /* tp_methods */
		0,                    /* tp_members */
		0,                    /* tp_getset */
		0,                    /* tp_base */
		0,                    /* tp_dict */
		0,                    /* tp_descr_get */
		0,                    /* tp_descr_set */
		0,                    /* tp_dictoffset */
		0,                    /* tp_init */
		0,                    /* tp_alloc */
		0,                    /* tp_new */
	};

	PyModuleDef embmodule =
	{
		PyModuleDef_HEAD_INIT,
		"emb", 0, -1, 0,
	};

	// Internal state
	PyObject* g_stdout;
	PyObject* g_stdout_saved;
	PyObject* g_stderr;
	PyObject* g_stderr_saved;

	PyMODINIT_FUNC PyInit_emb(void) {
		g_stdout = 0;
		g_stdout_saved = 0;
		g_stderr = 0;
		g_stderr_saved = 0;

		StdoutType.tp_new = PyType_GenericNew;
		StderrType.tp_new = PyType_GenericNew;
		if (PyType_Ready(&StdoutType) < 0 || PyType_Ready(&StderrType) < 0)
			return 0;

		PyObject* m = PyModule_Create(&embmodule);
		if (m) {
			Py_INCREF(&StdoutType);
			Py_INCREF(&StderrType);
			PyModule_AddObject(m, "Stdout", reinterpret_cast<PyObject*>(&StdoutType));
			PyModule_AddObject(m, "Stderr", reinterpret_cast<PyObject*>(&StderrType));
		}
		return m;
	}

	void set_stdout(stdout_write_type write) {
		if (!g_stdout) {
			g_stdout_saved = PySys_GetObject("stdout"); // borrowed
			g_stdout = StdoutType.tp_new(&StdoutType, 0, 0);
		}

		Stdout* impl = reinterpret_cast<Stdout*>(g_stdout);
		impl->write = write;
		PySys_SetObject("stdout", g_stdout);
	}

	void set_stderr(stdout_write_type write) {
		if (!g_stderr) {
			g_stderr_saved = PySys_GetObject("stderr"); // borrowed
			g_stderr = StderrType.tp_new(&StderrType, 0, 0);
		}

		Stdout* impl = reinterpret_cast<Stdout*>(g_stderr);
		impl->write = write;
		PySys_SetObject("stderr", g_stderr);
	}

	void reset_stdout() {
		if (g_stdout_saved)
			PySys_SetObject("stdout", g_stdout_saved);

		Py_XDECREF(g_stdout);
		g_stdout = 0;
	}

	void reset_stderr() {
		if (g_stderr_saved)
			PySys_SetObject("stderr", g_stderr_saved);

		Py_XDECREF(g_stderr);
		g_stderr = 0;
	}

} // namespace emb

int main() {
	PyImport_AppendInittab("emb", emb::PyInit_emb);
	Py_Initialize();
	PyImport_ImportModule("emb");

	PyRun_SimpleString("print(\'hello to console\')");

	std::string cached_code;
	while (true) {
		std::string code;
		getline(std::cin, code);
		code.append("\n");
		cached_code.append(code);
		if (code.find_last_of(':') == code.length() - 2) {
			continue;
		}
		// here comes the ***magic***
		std::string stdout_buffer;
		std::string stderr_buffer;
		// switch sys.stdout to custom handler
		emb::stdout_write_type stdout_writer = [&stdout_buffer](std::string s) { stdout_buffer += s; };
		emb::set_stdout(stdout_writer);
		emb::stdout_write_type stderr_writer = [&stderr_buffer](std::string s) { stderr_buffer += s; };
		emb::set_stderr(stderr_writer);

		PyRun_SimpleString(cached_code.c_str());
		cached_code.clear();
		// output what was written to buffer object
		std::cout << stdout_buffer;
		std::cerr << stderr_buffer;
	}
	emb::reset_stdout();
	emb::reset_stderr();
	Py_Finalize();
	return 0;
}

/*
#include <Python.h>
#include <string>

int main(int argc, char** argv) {
	std::string stdOutErr =
		"import sys\n\
class CatchOutErr:\n\
	def __init__(self):\n\
		self.value = ''\n\
	def write(self, txt):\n\
		self.value += txt\n\
catchOutErr = CatchOutErr()\n\
sys.stdout = catchOutErr\n\
sys.stderr = catchOutErr\n\
"; //this is python code to redirect stdouts/stderr

	Py_Initialize();
	PyObject *pModule = PyImport_AddModule("__main__"); //create main module
	PyRun_SimpleString(stdOutErr.c_str()); //invoke code to redirect
	PyRun_SimpleString("print(1+1)"); //this is ok stdout
	PyRun_SimpleString("1+a"); //this creates an error
	PyObject *catcher = PyObject_GetAttrString(pModule, "catchOutErr"); //get our catchOutErr created above
	PyErr_Print(); //make python print any errors
	PyObject *output = PyObject_GetAttrString(catcher, "value"); //get the stdout and stderr from our catchOutErr object

	printf("Here's the output:\n %s", PyBytes_AS_STRING(output)); //it's not in our C++ portion

	Py_Finalize();


	return 0;

}
*/