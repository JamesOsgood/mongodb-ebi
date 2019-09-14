# Import base test
import os
from datetime import datetime

from pymongo import MongoClient
import gridfs
from pysys.basetest import BaseTest

class PySysTest(BaseTest):

	def __init__ (self, descriptor, outsubdir, runner):
		BaseTest.__init__(self, descriptor, outsubdir, runner)

	def execute(self):
		client = MongoClient(self.project.MONGODB_CONNECTION_STRING.replace('~', '='))
		db = client.ebi_files
		fs = gridfs.GridFS(db)
		
		path = self.input
		files = []
		self.getFilesToProcess(path, files, ['.xml', '.pdf', '.jpg'])

		for file in files:
			self.log.info(f'Importing {file}')
			with open(file, 'rb') as f:
				ret = fs.put(f)
				self.log.info(ret)

	def getFilesToProcess(self, path, files, extensions):
		for item in os.listdir(path):
			fullpath = os.path.join(path, item)	
			if os.path.isdir(fullpath) and not item.startswith('.'):
				self.getFilesToProcess(fullpath, files, extensions)
			else:
				filename, fileext = os.path.splitext(fullpath)
				if fileext.lower() in extensions:
					files.append(fullpath)

	def validate(self):
		pass
